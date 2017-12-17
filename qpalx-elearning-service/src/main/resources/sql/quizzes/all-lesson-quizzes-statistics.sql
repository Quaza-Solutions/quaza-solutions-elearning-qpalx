-- Micro lesson Quizzes statistics
Select	QuizzesStudentHasAccess.LessonID,
        QuizzesStudentHasAccess.LessonName,
        QuizzesStudentHasAccess.ProficiencyRankingScaleFloor,
        QuizzesStudentHasAccess.ProficiencyRankingScaleCeiling,
		QuizzesStudentHasAccess.MicroLessonID,
		QuizzesStudentHasAccess.MicroLessonName,
		QuizzesStudentHasAccess.QuizID,
		QuizzesStudentHasAccess.QuizTitle,
		StudentMostRecentQuizExperiences.ProficiencyScore,
		StudentMostRecentQuizExperiences.QPalXTutorialContentType,
		StudentMostRecentQuizExperiences.LearningExperienceStartDate,
		StudentMostRecentQuizExperiences.LearningExperienceCompletedDate
From (
	-- Find all quizzes across all MicroLessons in the Lesson
	Select	qPell.ID As LessonID,
            qPell.LessonName,
            qPell.ProficiencyRankingScaleFloor,
            qPell.ProficiencyRankingScaleCeiling,
			qMell.ID As MicroLessonID,
			qMell.MicroLessonName,
			aLqz.ID As QuizID,
			aLqz.QuizTitle,
			qMell.ElementOrder
	From	QPalXELesson qPell
	Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
	Join  AdaptiveLearningQuiz aLqz on aLqz.QPalXEMicroLessonID = qMell.id -- Dont need left outter join on Quiz here bcos if A quiz doesnt exist there will be no Quiz score
	Where	qPell.ID = ?
) As QuizzesStudentHasAccess
Left 	Outer Join (
	--  Find performance data for quizzes for student
	Select	alz.ID,
			alz.ScorableActivityID,
			alz.ProficiencyScore,
			alz.QPalXTutorialContentType,
			alz.LearningExperienceStartDate,
			alz.LearningExperienceCompletedDate
	From	AdaptiveLearningExperience alz
	Inner  Join (
		Select 	QPalxUserID,
				ScorableActivityID,
				Max(LearningExperienceStartDate) AS MaxLearningExperienceStartDate
		From	AdaptiveLearningExperience
		Where 	QPalxUserID = ?
		Group	By QPalxUserID, ScorableActivityID
	) 	rs on rs.MaxLearningExperienceStartDate = alz.LearningExperienceStartDate and rs.QPalxUserID = alz.QPalxUserID
) As StudentMostRecentQuizExperiences on StudentMostRecentQuizExperiences.ScorableActivityID = QuizzesStudentHasAccess.QuizID
Order By QuizzesStudentHasAccess.ElementOrder asc
