package org.summerserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.summerserver.model.dao.CommentDAO;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.vo.Author;
import org.summerserver.model.vo.Comment;
import org.summerserver.model.vo.StatusUpdate;
import play.mvc.Result;

import java.util.List;

@Controller
public class CommentsServices extends play.mvc.Controller {
    @Autowired
    @Qualifier(value = "target")
    StatusUpdateDAO statusUpdateDAO;

    @Autowired
    @Qualifier(value = "target")
    CommentDAO commentDAO;

    private Result getResult(Object result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return ok(mapper.writeValueAsString(result));
    }

    public Result getComments(Long id) throws JsonProcessingException {
        StatusUpdate statusUpdate = statusUpdateDAO.findStatusWithComments(id);
        System.out.println("comments are!!! " + statusUpdate.getComments());
        statusUpdate.setJsonComments(statusUpdate.getComments());

        if (statusUpdate == null) {
            return notFound("Comment not found");
        }

        return getResult(statusUpdate);
    }

    public Result addComment(Long id) throws JsonProcessingException {
        Comment comment = null;
        StatusUpdate statusUpdate = statusUpdateDAO.getById(id, false);

        if (statusUpdate == null) {
            return notFound("Comment not found");
        }

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            comment = new Comment();
            String text = json.findPath("text").textValue();
            String author = json.findPath("author").textValue();

            comment.setMessage(text);
            comment.setAuthor(new Author(author));
            comment.setStatusUpdate(statusUpdate);
            //statusUpdate.addComment(comment);

            commentDAO.makePersistent(comment);
        }

        return getResult(comment);

        /*Comment comment = null;
        StatusUpdate statusUpdate = statusUpdateDAO.getById(id, false);

        if (statusUpdate == null) {
            return notFound("Status update not found");
        }

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            comment = new Comment();
            String text = json.findPath("text").textValue();
            String author = json.findPath("author").textValue();

            comment.setMessage(text);
            comment.setAuthor(new Author(author));
            comment = statusUpdateDAO.addComment(statusUpdate.getId(), comment);
        }

        return getResult(comment);*/
    }

    public Result addStatusUpdate() throws JsonProcessingException {
        JsonNode json = request().body().asJson();
        if (json == null) {
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
