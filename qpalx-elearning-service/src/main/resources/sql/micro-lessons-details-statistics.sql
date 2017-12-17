Select	MicroLessonQuizTotals.MicroLessonID,
		MicroLessonQuizTotals.MicroLessonName,
		MicroLessonQuizTotals.ELearningMediaFile,
		MicroLessonQuizTotals.StaticELearningMediaFile,
		MicroLessonQuizTotals.InteractiveExerciseELearningMediaFile,
		MicroLessonQuizTotals.ElementOrder,
		IFNULL(MicroLessonQuizTotals.TotalNumberOfQuizzes, 0) AS TotalNumberOfQuizzes,
		IFNULL(MicroLessonQuizAttemptTotals.UniqueQuizzesAttempted, 0) AS UniqueQuizzesAttempted
From (
		-- Micro Lessons total number of quizzes
		Select	qMell.ID As MicroLessonID,
				qMell.MicroLessonName,
				qMell.ELearningMediaFile,
				qMell.StaticELearningMediaFile,
				qMell.InteractiveExerciseELearningMediaFile,
				qMell.ElementOrder,
				count(alqz.ID) as TotalNumberOfQuizzes
		From	QPalXELesson qPell
		Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Left Outer Join 	AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
		Where	qPell.ID = ?
	Group   By qMell.ID, qMell.MicroLessonName, qMell.ELearningMediaFile, qMell.StaticELearningMediaFile, qMell.InteractiveExerciseELearningMediaFile, qMell.ElementOrder
) As MicroLessonQuizTotals
Left Outer Join (
		--  Micro Lessons total number of quiz attempts by Student
		Select	qMell.ID As MicroLessonID,
				qMell.MicroLessonName,
				qMell.ELearningMediaFile,
				qMell.StaticELearningMediaFile,
				qMell.InteractiveExerciseELearningMediaFile,
				qMell.ElementOrder,
				count(quizprog.AdaptiveLearningQuizID) As UniqueQuizzesAttempted
		From	QPalXELesson qPell
		Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Left Outer Join	AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
		Left Outer Join	AdaptiveLearningQuizProgress quizprog on quizprog.AdaptiveLearningQuizID = alqz.ID
		Where	qPell.ID = ?
		And		quizprog.QPalxUserID = ?
		Group   By qMell.ID, qMell.MicroLessonName, qMell.ELearningMediaFile, qMell.StaticELearningMediaFile, qMell.InteractiveExerciseELearningMediaFile, qMell.ElementOrder
) AS MicroLessonQuizAttemptTotals On MicroLessonQuizAttemptTotals.MicroLessonID = MicroLessonQuizTotals.MicroLessonID
Order By MicroLessonQuizTotals.ElementOrder ASC