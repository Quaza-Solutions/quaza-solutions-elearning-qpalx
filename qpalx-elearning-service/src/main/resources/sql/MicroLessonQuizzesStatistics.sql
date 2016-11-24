Select	qUser.ID As StudentID,
		qMell.ID As MicroLessonID,
		qMell.MicroLessonName,
		aLqz.ID As QuizID,
		aLqz.ScorableActivityID,
		aLqz.ScorableActivityName,
		aLex.QPalXTutorialContentType,
		aLex.ProficiencyScore,
		aLex.LearningExperienceStartDate,
		aLex.LearningExperienceCompletedDate
From	QPalXUser qUser
Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
Left 	Outer Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
Left	Outer Join  AdaptiveLearningQuiz aLqz on aLqz.`QPalXEMicroLessonID` = qMell.id
Left	Outer Join	AdaptiveLearningExperience aLex on aLex.ScorableActivityID = aLqz.ScorableActivityID
Where	qUser.ID = ?
And		qMell.ID = ?