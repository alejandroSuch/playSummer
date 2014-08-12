package org.summerserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.vo.Author;
import org.summerserver.model.vo.StatusUpdate;
import play.mvc.Result;

/*import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;*/
import java.util.List;
//import java.util.Set;

@Controller
public class StatusUpdateServices extends play.mvc.Controller {
    @Autowired
    @Qualifier(value = "target")
    StatusUpdateDAO statusUpdateDAO;

    private Result getResult(Object result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return ok(mapper.writeValueAsString(result));
    }

    public Result getStatusUpdates(int page) throws JsonProcessingException {
        List<StatusUpdate> statusUpdates = statusUpdateDAO.getAllStatusUpdates(page);
        return getResult(statusUpdates);
    }

    public Result getStatusUpdatesByRelevance(int page) throws JsonProcessingException {
        List<StatusUpdate> statusUpdates = statusUpdateDAO.getAllStatusUpdatesByRelevance(page);
        return getResult(statusUpdates);
    }

    public Result getUserStatusUpdates(String user, int page) throws JsonProcessingException {
        List<StatusUpdate> statusUpdates = statusUpdateDAO.getAllStatusUpdatesByUser(user, page);
        return getResult(statusUpdates);
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

            /*ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<StatusUpdate>> errors = validator.validate(statusUpdate);*/

            //if(errors.size() > 0) {
                StatusUpdate result = statusUpdateDAO.makePersistent(statusUpdate);
                return getResult(result);
            /*} else {
                return getResult(errors);
            }*/

        }
    }
}