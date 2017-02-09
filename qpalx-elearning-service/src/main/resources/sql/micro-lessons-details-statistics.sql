Select	StudentMicroLessonQuizTotals.StudentID, StudentMicroLessonQuizTotals.MicroLessonID, StudentMicroLessonQuizTotals.MicroLessonName, StudentMicroLessonQuizTotals.ELearningMediaFile, StudentMicroLessonQuizTotals.StaticELearningMediaFile, StudentMicroLessonQuizTotals.TotalNumberOfQuizzes, IFNULL(StudentMicroLessonQuizAttempts.UniqueQuizzesAttempted, 0)
		As	UniqueQuizzesAttempted
From    (
                                   Select    qUser.ID As StudentID, qMell.ID As MicroLessonID, qMell.MicroLessonName, qMell.ELearningMediaFile, qMell.StaticELearningMediaFile, count(alqz.ID) as TotalNumberOfQuizzes
                                   From      QPalXUser qUser
                                   Join      StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
                                   Join      ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
                                   Join      ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
                                   Join      QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
                                   Join      QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
                                   Left      Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
                                   Where     qUser.ID = ?
                                   And       eCors.ID = ?
                                   And       qPell.ID = ?
                                   Group     By qUser.ID, qMell.ID,  qMell.MicroLessonName, qMell.ELearningMediaFile, qMell.StaticELearningMediaFile
		) As StudentMicroLessonQuizTotals
Left 	Outer Join (
                                   Select    quizprog.QPalxUserID As StudentID, qMell.ID As MicroLessonID, qMell.MicroLessonName, qMell.ELearningMediaFile, qMell.StaticELearningMediaFile, count(quizprog.AdaptiveLearningQuizID) As UniqueQuizzesAttempted
                                   From      QPalXELesson qPell
                                   Left      Outer Join QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
                                   Left      Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
                                   Left      Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.AdaptiveLearningQuizID = alqz.ID
                                   Where     qPell.ID = ?
                                   And       quizprog.QPalxUserID = ?
                                   Group     By quizprog.QPalxUserID, qMell.ID,  qMell.MicroLessonName, qMell.ELearningMediaFile, qMell.StaticELearningMediaFile
) As StudentMicroLessonQuizAttempts on StudentMicroLessonQuizAttempts.StudentID = StudentMicroLessonQuizTotals.StudentID
Group	BY StudentMicroLessonQuizTotals.StudentID, StudentMicroLessonQuizTotals.MicroLessonID, StudentMicroLessonQuizTotals.MicroLessonName, StudentMicroLessonQuizTotals.ELearningMediaFile, StudentMicroLessonQuizTotals.StaticELearningMediaFile, StudentMicroLessonQuizTotals.TotalNumberOfQuizzes,  StudentMicroLessonQuizAttempts.UniqueQuizzesAttempted