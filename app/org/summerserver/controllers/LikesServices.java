package org.summerserver.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.vo.StatusUpdate;
import play.mvc.Result;

@Controller
public class LikesServices extends play.mvc.Controller {
    @Autowired
    @Qualifier(value = "target")
    StatusUpdateDAO statusUpdateDAO;

    public Result like() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            String id = json.findPath("status").textValue();
            StatusUpdate statusUpdate = statusUpdateDAO.getById(id, false);

            if (statusUpdate == null) {
                return notFound("Status update not found");
            }

            statusUpdate.addLike();
            statusUpdateDAO.makePersistent(statusUpdate);
        }

        return ok();
    }
}