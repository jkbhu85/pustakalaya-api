package com.jk.ptk.util.mail;

import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.f.user.User;
import com.jk.ptk.mail.BookAssignedMail;

public interface MailHelper {
	
	void sendMailOnBookAssigned(BookAssignedMail obj);
	
	void sendMailOnAccountCreation(NewUser newUser);
	
	void sendMailOnAccountCreationCompletion(User user);
}
