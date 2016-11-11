Select	StudentLessonsTotalMicroLessons.StudentID,
		StudentLessonsTotalMicroLessons.LessonID,
		StudentLessonsTotalMicroLessons.LessonName,
		StudentLessonsTotalMicroLessons.LessonIntroVideo,
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
) As StudentLessonsTotalMicroLessons
Left 	Outer Join (
	Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			count(qBit.ID) As TotalNumberOfQuestionBankItems
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Join	QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	Group 	By  qUser.ID, qPell.ID, qPell.LessonName
) As StudentQuestionBankItemsInLesson  on StudentQuestionBankItemsInLesson.StudentID = StudentLessonsTotalMicroLessons.StudentID
Left Outer Join (
	Select	qUser.ID As StudentID,
			qPell.ID As LessonID,
			qPell.LessonName,
			qPell.ELearningMediaFile As LessonIntroVideo,
			count(alqz.ID) As TotalNumberOfQuizzes
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
	Left 	Outer Join  AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
	Where	qUser.ID = ?
	And		eCors.ID = ?
	Group 	By qUser.ID, qPell.ID, qPell.LessonName, qPell.ELearningMediaFile
) As StudentTotalQuizzes on StudentTotalQuizzes.StudentID = StudentQuestionBankItemsInLesson.StudentID
Left Outer Join (
	Select 	qBpro.QPalxUserID,
			qPell.ID As LessonID,
			qPell.LessonName,
        	count(qBpro.ID) as UniqueQuestionBankItemsAttempted
	From   	QPalXELesson qPell
	Left   	Outer Join  QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID
	Left   	Outer Join  QuestionBankProgress qBpro on qBpro.QuestionBankItemID = qBit.ID
	Left   	Outer Join TutorialLevelCalendar tlc on tlc.ID = qPell.TutorialLevelCalendarID
	Where  	qPell.ELearningCourseID = ?
	And    	tlc.ID = ?
	Group  	By qBpro.QPalxUserID, qPell.ID, qPell.LessonName
) As StudentQuestionBankItemsAttempted on StudentQuestionBankItemsAttempted.QPalxUserID = StudentTotalQuizzes.StudentID
Left	Outer Join (
	Select 	mlp.QPalxUserID,
			qpl.ID As LessonID,
			qpl.LessonName,
			qpl.ELearningMediaFile As LessonIntroVideo,
        	count(mlp.MicroLessonID) As UniqueMicroLessonsAttempted
   	From   	QPalXELesson qpl
	Left   	Outer Join  QPalXEMicroLesson ml on ml.QPalXELessonID = qpl.ID
	Left   	Outer Join  QPalXEMicroLessonProgress mlp on mlp.MicroLessonID = ml.ID
	Left   	Outer Join TutorialLevelCalendar tlc on tlc.ID = qpl.TutorialLevelCalendarID
	Where   qpl.ELearningCourseID = ?
	And    	tlc.ID = ?
	Group  	By mlp.QPalxUserID, qpl.ID, qpl.LessonName, qpl.ELearningMediaFile
) As StudentUniqueMicroLessonsAttempt on StudentUniqueMicroLessonsAttempt.QPalxUserID = StudentQuestionBankItemsAttempted.QPalxUserID
Left	Outer Join (
	Select 		quizprog.QPalxUserID,
			qpl.ID As LessonID,
			qpl.LessonName,
			qpl.ELearningMediaFile As LessonIntroVideo,
        	count(quizprog.ID) as UniqueQuizzesAttempted
	From   	QPalXELesson qpl
	Left   	Outer Join  QPalXEMicroLesson ml on ml.QPalXELessonID = qpl.ID
	Left   	Outer Join  AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = ml.ID
	Left   	Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.MicroLessonID = ml.ID
	Left   	Outer Join TutorialLevelCalendar tlc on tlc.ID = qpl.TutorialLevelCalendarID
	Where  	qpl.ELearningCourseID = ?
	And    	tlc.ID = ?
	And		quizprog.QPalxUserID = ?
	Group  	By quizprog.QPalxUserID, qpl.ID, qpl.LessonName, qpl.ELearningMediaFile
) As StudentUniqueQuizAttempt on StudentUniqueQuizAttempt.QPalxUserID = StudentUniqueMicroLessonsAttempt.QPalxUserID