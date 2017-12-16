Select	AllStudentLessons.StudentID,
		AllStudentLessons.LessonID,
		AllStudentLessons.LessonName,
		AllStudentLessons.ProficiencyRankingScaleFloor,
        AllStudentLessons.ProficiencyRankingScaleCeiling,
		AllStudentLessons.LessonIntroVideo,
		StudentQuestionBankItemsInLesson.TotalNumberOfQuestionBankItems,
		IFNULL(StudentQuestionBankItemsAttempted.UniqueQuestionBankItemsAttempted, 0) As UniqueQuestionBankItemsAttempted,
		StudentLessonsTotalMicroLessons.TotalNumberOfMicroLessons,
		IFNULL(StudentUniqueMicroLessonsAttempt.UniqueMicroLessonsAttempted, 0) As UniqueMicroLessonsAttempted,
		StudentTotalQuizzes.TotalNumberOfQuizzes,
		IFNULL(StudentUniqueQuizAttempt.UniqueQuizzesAttempted, 0) As UniqueQuizzesAttempted
From	(
		Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			qPell.ProficiencyRankingScaleFloor,
            qPell.ProficiencyRankingScaleCeiling,
			qPell.ELearningMediaFile As LessonIntroVideo,
			qPell.ElementOrder
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Left   	Outer Join TutorialLevelCalendar tlc on tlc.ID = qPell.TutorialLevelCalendarID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	And    	tlc.ID = ?
	Group 	By qUser.ID, qPell.ID, qPell.LessonName, qPell.ELearningMediaFile, qPell.ElementOrder
) As AllStudentLessons
Left Outer Join(
		Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			qPell.ELearningMediaFile As LessonIntroVideo,
			count(qMell.ID) As TotalNumberOfMicroLessons
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	Group 	By qUser.ID, qPell.ID, qPell.LessonName, qPell.ELearningMediaFile
) As StudentLessonsTotalMicroLessons On StudentLessonsTotalMicroLessons.LessonID = AllStudentLessons.LessonID
Left 	Outer Join (
	Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			count(qBit.ID) As TotalNumberOfQuestionBankItems
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Left 	Outer Join	QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	Group 	By  qUser.ID, qPell.ID, qPell.LessonName
) As StudentQuestionBankItemsInLesson  on StudentQuestionBankItemsInLesson.LessonID = AllStudentLessons.LessonID
Left Outer Join (
	Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			qPell.ELearningMediaFile As LessonIntroVideo,
			count(alqz.ID) As TotalNumberOfQuizzes
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Left 	Outer Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
	Left 	Outer Join  AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	Group 	By qUser.ID, qPell.ID, qPell.LessonName, qPell.ELearningMediaFile
) As StudentTotalQuizzes on StudentTotalQuizzes.LessonID = AllStudentLessons.LessonID
Left Outer Join (
	Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			qPell.ProficiencyRankingScaleFloor,
            qPell.ProficiencyRankingScaleCeiling,
			qPell.ELearningMediaFile As LessonIntroVideo,
			count(qBpro.ID) as UniqueQuestionBankItemsAttempted
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Left   	Outer Join  QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID
	Left   	Outer Join  QuestionBankProgress qBpro on qBpro.QuestionBankItemID = qBit.ID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	And		qBpro.QPalxUserID = ?
	Group 	By qUser.ID, qPell.ID, qPell.LessonName, qPell.ProficiencyRankingScaleFloor, qPell.ProficiencyRankingScaleCeiling, qPell.ELearningMediaFile
) As StudentQuestionBankItemsAttempted on StudentQuestionBankItemsAttempted.LessonID = AllStudentLessons.LessonID
Left	Outer Join (
	Select 	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			qPell.ELearningMediaFile As LessonIntroVideo,
        	count(mlp.MicroLessonID) As UniqueMicroLessonsAttempted
   	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Left   	Outer Join  QPalXEMicroLesson ml on ml.QPalXELessonID = qPell.ID
	Left   	Outer Join  QPalXEMicroLessonProgress mlp on mlp.MicroLessonID = ml.ID
	Where   qUser.ID = ?
	And		eCors.ID = ?
	And		mlp.QPalxUserID = ?
	Group  	By mlp.QPalxUserID, qPell.ID, qPell.LessonName, qPell.ELearningMediaFile
) As StudentUniqueMicroLessonsAttempt on StudentUniqueMicroLessonsAttempt.LessonID = AllStudentLessons.LessonID
Left	Outer Join (
	Select 	qPell.ID as LessonID,
    		qPell.LessonName,
    		count(quizprog.ID) as UniqueQuizzesAttempted
    From	AdaptiveLearningQuizProgress quizprog
    Join  	AdaptiveLearningQuiz alqz on alqz.ID = quizprog.AdaptiveLearningQuizID
    Join  	QPalXEMicroLesson qMell on qMell.ID = quizprog.MicroLessonID
    Join	QPalXELesson qPell on qPell.ID = qMell.QPalXELessonID
    Where	quizprog.QPalxUserID = ?
    Group 	By qPell.ID, qPell.LessonName
) As StudentUniqueQuizAttempt on StudentUniqueQuizAttempt.LessonID = AllStudentLessons.LessonID
Group By AllStudentLessons.StudentID,
		AllStudentLessons.LessonID,
		AllStudentLessons.LessonName,
		AllStudentLessons.LessonIntroVideo,
		StudentQuestionBankItemsInLesson.TotalNumberOfQuestionBankItems,
		StudentQuestionBankItemsAttempted.UniqueQuestionBankItemsAttempted,
		StudentLessonsTotalMicroLessons.TotalNumberOfMicroLessons,
		StudentUniqueMicroLessonsAttempt.UniqueMicroLessonsAttempted,
		StudentTotalQuizzes.TotalNumberOfQuizzes,
		StudentUniqueQuizAttempt.UniqueQuizzesAttempted
Order By AllStudentLessons.ElementOrder

