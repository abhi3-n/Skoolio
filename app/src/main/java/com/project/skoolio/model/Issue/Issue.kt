package com.project.skoolio.model.Issue

data class Issue(val issueId:String,
            val creationTime:Long, //will be an epoch value
            val creatorType:Char,
            val creatorId:String,
            val schoolId:Int,
            val classId:String,
            val title:String,
            val description:String,
            val issueMessages:List<IssueMessage>,
            val status:Char,
    )

data class IssueMessage(
    val messageCreatorId:String,
    val messageCreatorType:Char,
    val messageText:String,
    val messageTime:Long
)
