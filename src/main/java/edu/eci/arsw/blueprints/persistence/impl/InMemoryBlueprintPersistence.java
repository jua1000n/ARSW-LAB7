/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    //private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();
    private final ConcurrentMap<Tuple<String,String>,Blueprint> blueprints = new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(150, 10),new Point(145, 112)};
        Point[] pts1 = new Point[]{new Point(13, 10), new Point(25, 20)};
        Point[] pts2 = new Point[]{new Point(100, 50), new Point(15, 20)};
        Point[] pt3=new Point[]{new Point(150, 10),new Point(145, 112)};
        Point[] pts4 = new Point[]{new Point(13, 10), new Point(25, 20)};
        Point[] pts5 = new Point[]{new Point(100, 50), new Point(15, 20)};
        Blueprint bp1 = new Blueprint("bad", "blueprint01",pts1);
        Blueprint bp2 = new Blueprint("bad", "blueprin02", pts2);
        Blueprint bp3 = new Blueprint("other", "otroPlano", pt3);
        Blueprint bp4 = new Blueprint("juan", "bluepri02",pts4);
        Blueprint bp7 = new Blueprint("juan", "bluepri03",pts5);
        Blueprint bp5 = new Blueprint("laura", "bluepri03", pts5);
        Blueprint bp6 = new Blueprint("arturo", "otroPlano01", pts);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        blueprints.put(new Tuple<>(bp5.getAuthor(),bp5.getName()), bp5);
        blueprints.put(new Tuple<>(bp6.getAuthor(),bp6.getName()), bp6);
        blueprints.put(new Tuple<>(bp7.getAuthor(),bp7.getName()), bp7);

        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsReturn = new HashSet<Blueprint> ();
        for (Tuple<String,String> a: blueprints.keySet()) {
            if (author.equals(a.getElem1())) {
                blueprintsReturn.add(blueprints.get(a));
            }
        }
        return blueprintsReturn;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsReturn = new HashSet<Blueprint> ();
        for (Tuple<String,String> a: blueprints.keySet()) {
            blueprintsReturn.add(blueprints.get(a));
        }
        return blueprintsReturn;
    }

    @Override
    public void deleteBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        blueprints.remove(new Tuple<>(author, bprintname));
    }
}
