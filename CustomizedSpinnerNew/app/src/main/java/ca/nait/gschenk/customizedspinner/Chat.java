package ca.nait.gschenk.customizedspinner;

public class Chat
{
    private String chatSender;
    private String chatDate;
    private String chatContent;

    public Chat()
    {
        chatContent = "";
        chatDate = "";
        chatSender = "";
    }

    public Chat(String chatSender, String chatDate, String chatContent)
    {
        this.chatSender = chatSender;
        this.chatDate = chatDate;
        this.chatContent = chatContent;
    }

    public String getChatSender()
    {
        return chatSender;
    }

    public void setChatSender(String chatSender)
    {
        this.chatSender = chatSender;
    }

    public String getChatDate()
    {
        return chatDate;
    }

    public void setChatDate(String chatDate)
    {
        this.chatDate = chatDate;
    }

    public String getChatContent()
    {
        return chatContent;
    }

    public void setChatContent(String chatContent)
    {
        this.chatContent = chatContent;
    }
}
