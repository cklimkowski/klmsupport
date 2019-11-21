# klmsupport
Enterprise-level Customer Support Application
# Model View Controler plus Controller Service Repository
Here goes some theory MVC + CSR

# The flow of the process
Initial point is in the file IndexController.java:
```java
@WebController
public class IndexController
{
    @RequestMapping("/")
    public View index()
    {
        return new RedirectView("/ticket/list", true, false);
    }
}
```
where an user is transferred to ..

#Entities relationship
One-To-Many:
- Ticket to Attachment*s*
- TicketComment to Attachment*s*
- UserPrincipal to Ticket*s*
- UserPrincipal to TicketComment*s*

The joining mapping of the Ticket and Attachment is:
```java
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name = "Ticket_Attachment",
            joinColumns = { @JoinColumn(name = "TicketId") },
            inverseJoinColumns = { @JoinColumn(name = "AttachmentId") })
    @OrderColumn(name = "SortKey")
    @XmlElement(name = "attachment")
    @JsonProperty
    public List<Attachment> getAttachments()
    {
        return this.attachments;
    }
```
The joining mapping of the TicketComment and Attachment is:
```java
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name = "TicketComment_Attachment",
            joinColumns = { @JoinColumn(name = "CommentId") },
            inverseJoinColumns = { @JoinColumn(name = "AttachmentId") })
    @OrderColumn(name = "SortKey")
    @XmlElement(name = "attachment")
    @JsonProperty
    public List<Attachment> getAttachments()
    {
        return this.attachments;
    }
```
While listing tickets there is no need to list their attachments and that is the reason of lazy loading. But when the chosen ticket is being viewed its attachments need to be loaded. And I achieve that inside *DefaultTicketService* by calling a method *getNumberOfAttachments()* on the attachment list during the transaction. This method calls the *size*
 method of the *List<Attachment>* and thanks to it Hibernate loads the attachments for the individually retrieved ticket.
```java
    @Override
    @Transactional
    public Ticket getTicket(long id)
    {
        Ticket ticket = this.ticketRepository.findOne(id);
        ticket.getNumberOfAttachments();
        return ticket;
    }
```
The different situation is when listing the comments while viewing a ticket theie attachments need to be loaded. So they must be loaded eagerly.
  
Here goes some theory of lazy loading simple properties...
