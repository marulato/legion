package org.zenith.legion.routine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zenith.legion.common.base.AjaxResponseBody;
import org.zenith.legion.common.base.AjaxResponseManager;
import org.zenith.legion.routine.dto.CalendarEvent;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarAjaxController {

    @GetMapping("/web/events")
    public AjaxResponseBody getEvents() {
        AjaxResponseManager builder = AjaxResponseManager.create(200);
        CalendarEvent event = new CalendarEvent();
        event.setTitle("测试");
        event.setStart("2020-08-06");
        event.setAllDay(true);


        CalendarEvent event2 = new CalendarEvent();
        event2.setTitle("鬼啊");
        event2.setStart("2020-08-06 10:00");


        CalendarEvent event3 = new CalendarEvent();
        event3.setTitle("哈哈哈哈哈");
        event3.setStart("2020-08-06 15:00");

        List<CalendarEvent> list = new ArrayList<>();
        list.add(event);
        list.add(event2);
        list.add(event3);
        builder.addDataObjects(list);
        return builder.respond();
    }


}
