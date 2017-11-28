Select	StudentLessonsStats.StudentID,
		StudentLessonsStats.CurriculumID,
		StudentLessonsStats.CurriculumName,
		StudentLessonsStats.CurriculumDescription,
		StudentLessonsStats.CurriculumIcon,
		StudentLessonsStats.CurriculumType,
		StudentLessonsStats.TotalNumberOfLessons,
		StudentMicroLessonsStats.TotalNumberOfMicroLessons,
		StudentQuizzesStats.TotalNumberOfQuizzes,
		StudentQuestionBankStats.TotalNumberOfQuestionBankItems,
		StudentMicroLessonAttempts.UniqueMicroLessonsAttempted,
		IFNULL(StudentQuizAttempts.UniqueQuizzesAttempted, 0)  As UniqueQuizzesAttempted,
		IFNULL(StudentQuestionBankAttempts.UniqueQuestionBankItemsAttempted, 0) As UniqueQuestionBankItemsAttempted
From	(
		Select	qUser.ID As StudentID,
				eCurr.ID As CurriculumID,
				eCurr.CurriculumName,
				eCurr.CurriculumDescription,
				eCurr.CurriculumIcon,
				eCurr.CurriculumType,
				count(qPell.ID) As TotalNumberOfLessons
		From	QPalXUser qUser
		Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
		Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
		Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
		Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
		Where	qUser.ID = ?
		And		eCurr.CurriculumType = ?
		And     eCurr.Active = 1
		Group 	By qUser.ID, eCurr.ID, eCurr.CurriculumName, eCurr.CurriculumDescription, eCurr.CurriculumIcon, eCurr.CurriculumType
) As StudentLessonsStats
Left 	Outer Join (
		Select	qUser.ID As StudentID,
				eCurr.ID As CurriculumID,
				eCurr.CurriculumName,
				eCurr.CurriculumType,
				count(qMell.ID) As TotalNumberOfMicroLessons
		From	QPalXUser qUser
		Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
		Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
		Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
		Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
		Left 	Outer Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Where	qUser.ID = ?
		And		eCurr.CurriculumType = ?
		And     eCurr.Active = 1
		Group 	By qUser.ID, eCurr.ID, eCurr.CurriculumName, eCurr.CurriculumType
) As StudentMicroLessonsStats ON StudentMicroLessonsStats.CurriculumID = StudentLessonsStats.CurriculumID
Left 	Outer Join (
		Select	qUser.ID As StudentID,
				eCurr.ID As CurriculumID,
				eCurr.CurriculumName,
				eCurr.CurriculumType,
				count(alqz.ID) As TotalNumberOfQuizzes
		From	QPalXUser qUser
		Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
		Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
		Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
		Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
		Left 	Outer Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Left 	Outer Join  AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID  
		Where	qUser.ID = ?
		And		eCurr.CurriculumType = ?
		And     eCurr.Active = 1
		Group 	By qUser.ID, eCurr.ID, eCurr.CurriculumName, eCurr.CurriculumType
) As StudentQuizzesStats ON StudentQuizzesStats.CurriculumID = StudentMicroLessonsStats.CurriculumID
Left	Outer Join (
		Select	qUser.ID As StudentID,
				eCurr.ID As CurriculumID,
				eCurr.CurriculumName,
				eCurr.CurriculumType,
				count(qBit.ID) As TotalNumberOfQuestionBankItems
		From	QPalXUser qUser
		Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
		Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
		Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
		Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
		Left 	Outer Join	QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID 
		Where	qUser.ID = ?
		And		eCurr.CurriculumType = ?
		And     eCurr.Active = 1
		Group 	By qUser.ID, eCurr.ID, eCurr.CurriculumName, eCurr.CurriculumType
) As StudentQuestionBankStats  On StudentQuestionBankStats.CurriculumID = StudentQuizzesStats.CurriculumID
Left	Outer Join(
		Select	qUser.ID As StudentID,
				eCurr.ID As CurriculumID,
				eCurr.CurriculumName,
				eCurr.CurriculumType,
				count(mlp.MicroLessonID) As UniqueMicroLessonsAttempted
		From	QPalXUser qUser
		Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
		Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
		Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
		Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
		Left 	Outer Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Left   	Outer Join  QPalXEMicroLessonProgress mlp on mlp.MicroLessonID = qMell.ID  
		Where	qUser.ID = ?
		And		eCurr.CurriculumType = ?
		And     eCurr.Active = 1
		Group 	By qUser.ID, eCurr.ID, eCurr.CurriculumName, eCurr.CurriculumType
) As StudentMicroLessonAttempts ON StudentMicroLessonAttempts.CurriculumID = StudentQuestionBankStats.CurriculumID
Left	Outer Join(	
		Select 	eCurr.ID as CurriculumID,
        		eCurr.CurriculumName,
        		count(quizprog.ID) as UniqueQuizzesAttempted
        From	AdaptiveLearningQuizProgress quizprog
        Join  	AdaptiveLearningQuiz alqz on alqz.ID = quizprog.AdaptiveLearningQuizID
        Join  	QPalXEMicroLesson qMell on qMell.ID = quizprog.MicroLessonID
        Join	QPalXELesson qPell on qPell.ID = qMell.QPalXELessonID
        Join	ELearningCourse eCors on eCors.ID = qPell.ELearningCourseID
        Join	ELearningCurriculum eCurr on eCurr.ID = eCors.ELearningCurriculumID
        Where	quizprog.QPalxUserID = ?
        And		eCurr.CurriculumType = ?
        And     eCurr.Active = 1
        Group 	By eCurr.ID, eCurr.CurriculumName
) As StudentQuizAttempts On StudentQuizAttempts.CurriculumID = StudentMicroLessonAttempts.CurriculumID
Left	Outer Join(
		Select	qUser.ID As StudentID,
				eCurr.ID As CurriculumID,
				eCurr.CurriculumName,
				eCurr.CurriculumType,
				count(qBpro.ID) as UniqueQuestionBankItemsAttempted
		From	QPalXUser qUser
		Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
		Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
		Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
		Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
		Left   	Outer Join  QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID  
		Left   	Outer Join  QuestionBankProgress qBpro on qBpro.QuestionBankItemID = qBit.ID     
		Where	qUser.ID = ?
		And		eCurr.CurriculumType = ?
		And     eCurr.Active = 1
		Group 	By qUser.ID, eCurr.ID, eCurr.CurriculumName, eCurr.CurriculumType
) As StudentQuestionBankAttempts ON StudentQuestionBankAttempts.CurriculumID = StudentQuizAttempts.CurriculumID