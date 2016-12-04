Select	 SUM(TotalCount) As TotalLessonItems
From
(
		-- Find count of all MicroLessons for Lesson
		Select	count(qMell.ID) As TotalCount
		From	QPalXELesson qPell
		Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Where	qPell.ID = ?

		UNION ALL

		-- Find count of all MicroLessons quizzes for Lesson
		Select	count(alqz.ID) As TotalCount
		From	QPalXELesson qPell
		Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
		Join  	AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
		Where	qPell.ID = ?

		UNION ALL

		-- Find count of all QuestionBaknks for Lesson
		Select	count(qBit.ID) As TotalCount
		From	QPalXELesson qPell
		Join	QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID
		Where	qPell.ID = ?

) As CountMicroLessons