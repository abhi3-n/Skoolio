package com.project.skoolio.model.Issue

data class Issue(val issueId:String,
                 val creationTime:Long, //will be an epoch value
                 val creatorType:Char,
                 val creatorId:String,
                 val schoolId:Int,
                 val classId:String,
                 val title:String,
                 val description:String,
                 var issueMessages:List<IssueMessage>,
                 val status:Char,
    )

open class IssueMessage(
    val messageCreatorId:String,
    val messageCreatorType:Char,
    val messageText:String,
    val messageTime:Long
)


class IssueMessageRequest(
    messageCreatorId: String,
    messageCreatorType: Char,
    messageText: String,
    messageTime: Long,
    val issueId:String
) :IssueMessage(messageCreatorId, messageCreatorType, messageText, messageTime){
    companion object {
        fun getMessageRequest(issueMessage: IssueMessage, issueId: String): IssueMessageRequest {
            return IssueMessageRequest(issueMessage.messageCreatorId, issueMessage.messageCreatorType, issueMessage.messageText,issueMessage.messageTime, issueId)
        }
    }

}