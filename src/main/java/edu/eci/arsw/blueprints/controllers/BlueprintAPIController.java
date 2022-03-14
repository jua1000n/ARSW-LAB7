/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonToken;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoXX() {
        try {
            Set<Blueprint> blueprintSet = blueprintsServices.getAllBlueprints();
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(new Gson().toJson(blueprintsJson(blueprintSet)), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprintSet = blueprintsServices.getBlueprintsByAuthor(author);
            //obtener datos que se enviarán a través del API
            if (blueprintSet.size() != 0) {
                return new ResponseEntity<>(new Gson().toJson(authorJson(blueprintSet)), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            Logger.getLogger(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint blueprintSet = blueprintsServices.getBlueprint(author, bpname);
            //obtener datos que se enviarán a través del API
            if (blueprintSet == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } else {
                return new ResponseEntity<>(new Gson().toJson(authorNameJson(blueprintSet)), HttpStatus.ACCEPTED);
            }

        } catch (Exception ex) {
            //Logger.getLogger(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecurso(@RequestBody Blueprint blueprint){
        try {
            //registrar dato
            blueprintsServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/{author}/{bpname}",method = RequestMethod.PUT)
    public ResponseEntity<?> manejadorPutRecurso(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint blueprint){
        try {
            //registrar dato
            blueprintsServices.deleteBlueprint(author, bpname);
            blueprintsServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    private JsonArray authorNameJson(Blueprint blueprint) {
        JsonArray jsonArray = new JsonArray();
        for (Point a:blueprint.getPoints()) {
            JsonObject p = new JsonObject();
            p.addProperty("x", a.getX());
            p.addProperty("y", a.getY());
            jsonArray.add(p);
        }
        return jsonArray;
    }

    private JsonArray authorJson(Set<Blueprint> blueprint) {
        JsonArray jsonArray = new JsonArray();
        for (Blueprint a:blueprint) {
            JsonObject p = new JsonObject();
            p.addProperty("author", a.getAuthor());
            p.addProperty("name", a.getName());
            p.add("points",authorNameJson(a));
            jsonArray.add(p);
        }
        return jsonArray;
    }

    private JsonObject blueprintsJson(Set<Blueprint> blueprint) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("blueprints", authorJson(blueprint));
        return jsonObject;
    }
}

