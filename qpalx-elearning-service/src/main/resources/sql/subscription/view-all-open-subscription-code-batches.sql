Select	AllSubscriptionBatchPrePaidCodes.ID,
        AllSubscriptionBatchPrePaidCodes.PlatformPartnerName,
		AllSubscriptionBatchPrePaidCodes.PlatformPartnerType,
		AllSubscriptionBatchPrePaidCodes.SubscriptionCodeBatchSessionUID,
		AllSubscriptionBatchPrePaidCodes.BatchGenerationDate,
		AllSubscriptionBatchPrePaidCodes.BatchRedemptionCompletionDate,
		IFNULL(RedeemedSubscriptionBatchPrePaidCodes.TotalNumberOfRedeemedPrepaidCodes, 0) As TotalNumberOfRedeemedPrepaidCodes,
		OpenSubscriptionBatchPrePaidCodes.TotalNumberOfOpenPrePaidCodes,
		AllSubscriptionBatchPrePaidCodes.TotalNumberofPrePaidCodes

From	(

	--  Select All SubscriptionCodeBatchSession and matching PrepaidSubscription
	Select	sBatchSess.ID,
			sBatchSess.BatchGenerationDate,
			sBatchSess.BatchRedemptionCompletionDate,
			sBatchSess.SubscriptionCodeBatchSessionUID,
			splatPart.PlatformPartnerName,
			splatPart.PlatformPartnerType,
			count(ppaidSub.ID) As TotalNumberofPrePaidCodes
	From	SubscriptionCodeBatchSession sBatchSess
	Join	StrategicPlatformPartner splatPart on splatPart.ID = sBatchSess.StrategicPlatformPartnerID
	Join	PrepaidSubscription ppaidSub on  ppaidSub.SubscriptionCodeBatchSessionID = sBatchSess.ID
	Group By sBatchSess.BatchGenerationDate,
			sBatchSess.BatchRedemptionCompletionDate,
			sBatchSess.SubscriptionCodeBatchSessionUID,
			splatPart.PlatformPartnerName,
			splatPart.PlatformPartnerType
) As AllSubscriptionBatchPrePaidCodes
Left Outer Join (
	-- Find all Prepaid subscriptionCode in batch that is currently open
	Select	sBatchSess.ID,
			sBatchSess.BatchGenerationDate,
			sBatchSess.BatchRedemptionCompletionDate,
			sBatchSess.SubscriptionCodeBatchSessionUID,
			splatPart.PlatformPartnerName,
			splatPart.PlatformPartnerType,
			count(ppaidSub.ID) As TotalNumberOfOpenPrePaidCodes
	From	SubscriptionCodeBatchSession sBatchSess
	Join	StrategicPlatformPartner splatPart on splatPart.ID = sBatchSess.StrategicPlatformPartnerID
	Join	PrepaidSubscription ppaidSub on  ppaidSub.SubscriptionCodeBatchSessionID = sBatchSess.ID
	Where	ppaidSub.AlreadyUsed = 0
	Group By sBatchSess.BatchGenerationDate,
			sBatchSess.BatchRedemptionCompletionDate,
			sBatchSess.SubscriptionCodeBatchSessionUID,
			splatPart.PlatformPartnerName,
			splatPart.PlatformPartnerType
) As OpenSubscriptionBatchPrePaidCodes On OpenSubscriptionBatchPrePaidCodes.ID = AllSubscriptionBatchPrePaidCodes.ID
Left Outer Join (
	--  Total number of redeemed PrePaid Subscription Items in Batch
	Select	sBatchSess.ID,
			sBatchSess.BatchGenerationDate,
			sBatchSess.BatchRedemptionCompletionDate,
			sBatchSess.SubscriptionCodeBatchSessionUID,
			splatPart.PlatformPartnerName,
			splatPart.PlatformPartnerType,
			count(ppaidSub.ID) As TotalNumberOfRedeemedPrepaidCodes
	From	SubscriptionCodeBatchSession sBatchSess
	Join	StrategicPlatformPartner splatPart on splatPart.ID = sBatchSess.StrategicPlatformPartnerID
	Join	PrepaidSubscription ppaidSub on  ppaidSub.SubscriptionCodeBatchSessionID = sBatchSess.ID
	Where	ppaidSub.AlreadyUsed = 1
	Group By sBatchSess.BatchGenerationDate,
			sBatchSess.BatchRedemptionCompletionDate,
			sBatchSess.SubscriptionCodeBatchSessionUID,
			splatPart.PlatformPartnerName,
			splatPart.PlatformPartnerType
) As RedeemedSubscriptionBatchPrePaidCodes On RedeemedSubscriptionBatchPrePaidCodes.ID = AllSubscriptionBatchPrePaidCodes.ID