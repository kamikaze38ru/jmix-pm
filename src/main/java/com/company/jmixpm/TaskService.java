package com.company.jmixpm;

import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private DataManager dataManager;

    public User findLeastBusyUser() {
        User leastBusyUser = dataManager.loadValues("select u, count(t.id) " + // (1)
                        "from User u left outer join Task_ t " +
                        "on u = t.assignee " +
                        "group by u order by count(t.id)")
                .properties("user", "tasks")
                .list().stream().map(e -> e.<User>getValue("user"))
                .findFirst() // (2)
                .orElseThrow(IllegalStateException::new);
        return leastBusyUser; // (3)
    }
}