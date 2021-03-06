Select	QuizzesStudentHasAccess.StudentID,
		QuizzesStudentHasAccess.MicroLessonID,
		QuizzesStudentHasAccess.MicroLessonName,
		QuizzesStudentHasAccess.QuizID,
		QuizzesStudentHasAccess.ScorableActivityID,
		QuizzesStudentHasAccess.ScorableActivityName,
		StudentMostRecentQuizExperiences.ProficiencyScore,
		StudentMostRecentQuizExperiences.QPalXTutorialContentType,
		StudentMostRecentQuizExperiences.LearningExperienceStartDate,
		StudentMostRecentQuizExperiences.LearningExperienceCompletedDate
From (
	-- Find all quizzes that user is entitled to
	Select	qUser.ID As StudentID,
			qMell.ID As MicroLessonID,
			qMell.MicroLessonName,
			aLqz.ID As QuizID,
			aLqz.ScorableActivityID,
			aLqz.ScorableActivityName 
	From	QPalXUser qUser
	Join	StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID
	Left 	Outer Join	ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID
	Left 	Outer Join	ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID
	Left 	Outer Join	QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID
	Left 	Outer Join	QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID
	Left	Outer Join  AdaptiveLearningQuiz aLqz on aLqz.`QPalXEMicroLessonID` = qMell.id
	Where	qUser.ID = ?
	And		qMell.ID = ?
) As QuizzesStudentHasAccess
Left 	Outer Join (
	--  Find performance data for quizzes for student
	Select	alz.ID, alz.ScorableActivityID, alz.ProficiencyScore, alz.`QPalXTutorialContentType`, alz.`LearningExperienceStartDate`, alz.`LearningExperienceCompletedDate`
	From	AdaptiveLearningExperience alz
	Inner  Join ( 
		Select 	QPalxUserID,
				ScorableActivityID, 
				Max(LearningExperienceStartDate) AS MaxLearningExperienceStartDate
		From	AdaptiveLearningExperience
		Where 	QPalxUserID = ?
		Group	By QPalxUserID, ScorableActivityID 
	) 	rs on rs.MaxLearningExperienceStartDate = alz.LearningExperienceStartDate and rs.QPalxUserID = alz.QPalxUserID
) As StudentMostRecentQuizExperiences on StudentMostRecentQuizExperiences.ScorableActivityID = QuizzesStudentHasAccess.ScorableActivityID
