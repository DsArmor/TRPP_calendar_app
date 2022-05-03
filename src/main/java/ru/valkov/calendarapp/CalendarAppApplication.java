package ru.valkov.calendarapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.valkov.calendarapp.invite.Invite;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.user.User;

@SpringBootApplication
public class CalendarAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(CalendarAppApplication.class, args);
	}

}
