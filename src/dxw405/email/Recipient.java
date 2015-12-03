package dxw405.email;

import javax.mail.Message;

public enum Recipient
{
	TO(Message.RecipientType.TO),
	CC(Message.RecipientType.CC),
	BCC(Message.RecipientType.BCC);

	private Message.RecipientType type;

	Recipient(Message.RecipientType type)
	{
		this.type = type;
	}

	public Message.RecipientType getType()
	{
		return type;
	}
}
