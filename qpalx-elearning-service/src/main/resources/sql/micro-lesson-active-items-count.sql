Select	count(alqz.ID) As TotalCount
From	QPalXEMicroLesson qMell
Join  	AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID
Where	qMell.ID = ?