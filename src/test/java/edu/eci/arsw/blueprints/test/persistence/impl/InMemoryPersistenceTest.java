/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
        
    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("john", "mypaint",pts0);
        ibpp.saveBlueprint(bp0);

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaints",pts);
        ibpp.saveBlueprint(bp);

        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp1=new Blueprint("john", "thepaint",pts1);
        ibpp.saveBlueprint(bp1);

        Point[] pts2=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp2=new Blueprint("mack", "mypaint",pts2);
        ibpp.saveBlueprint(bp2);

        Set<Blueprint> blueprintsReturn = new HashSet<Blueprint>();
        blueprintsReturn.add(bp0);
        blueprintsReturn.add(bp);
        blueprintsReturn.add(bp1);

        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprintsByAuthor(bp.getAuthor()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprintsByAuthor(bp.getAuthor()), blueprintsReturn);

    }

    //@Test
    public void getAllBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("john", "mypaint",pts0);
        ibpp.saveBlueprint(bp0);

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaints",pts);
        ibpp.saveBlueprint(bp);

        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp1=new Blueprint("john", "thepaint",pts1);
        ibpp.saveBlueprint(bp1);

        Point[] pts2=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp2=new Blueprint("mack", "mypaint",pts2);
        ibpp.saveBlueprint(bp2);

        Point[] pts3=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp3=new Blueprint("_authorname_", "_bpname_ ",pts);

        Set<Blueprint> blueprintsReturn = new HashSet<Blueprint>();
        blueprintsReturn.add(bp);
        blueprintsReturn.add(bp3);
        blueprintsReturn.add(bp2);
        blueprintsReturn.add(bp0);
        blueprintsReturn.add(bp1);


        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getAllBlueprints());

        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getAllBlueprints(), blueprintsReturn);

    }

    @Test
    public void getBlueprintTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);

        ibpp.saveBlueprint(bp);

        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaints",pts2);

        ibpp.saveBlueprint(bp2);

        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }
}
