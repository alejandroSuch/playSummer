package org.summerserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.vo.Author;
import org.summerserver.model.vo.Comment;
import org.summerserver.model.vo.StatusUpdate;
import play.mvc.Result;

@Controller
public class CommentsServices extends play.mvc.Controller {
    @Autowired
    @Qualifier(value = "target")
    StatusUpdateDAO statusUpdateDAO;

    private Result getResult(Object result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return ok(mapper.writeValueAsString(result));
    }

    public Result getComments(String id) throws JsonProcessingException {
        StatusUpdate statusUpdate = statusUpdateDAO.getById(id, false);

        if (statusUpdate == null) {
            return notFound("Comment not found");
        }
        statusUpdate.getComments(); //Force fetch??

        return getResult(statusUpdate);
    }

    public Result addComment(String id) throws JsonProcessingException {
        StatusUpdate statusUpdate = statusUpdateDAO.getById(id, false);
        if (statusUpdate == null) {
            return notFound("Comment not found");
        }

        JsonNode json = request().body().asJson();
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            Comment comment = new Comment();
            String text = json.findPath("text").textValue();
            String author = json.findPath("author").textValue();

            comment.setMessage(text);
            comment.setAuthor(new Author(author));
            statusUpdate.addComment(comment);

            statusUpdateDAO.makePersistent(statusUpdate);
        }





        return ok();
    }

    public Result addStatusUpdate() throws JsonProcessingException {
        JsonNode json = request().body().asJson();
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            StatusUpdate statusUpdate = new StatusUpdate();
            String text = json.findPath("text").textValue();
            String author = json.findPath("author").textValue();

            statusUpdate.setMessage(text);
            statusUpdate.setAuthor(new Author(author));

            statusUpdateDAO.makePersistent(statusUpdate);
        }

        return ok();
    }
}
