package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.FiltrPersistence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
public class Undersampling implements FiltrPersistence {

    @Override
    public Blueprint Filtr(Blueprint bp) {
        List<Point> pts = new ArrayList<>();
        for(int i = 0; i< (bp.getPoints().size()); i+=2) {
            pts.add(bp.getPoints().get(i));

        }
        return new Blueprint(bp.getAuthor(), bp.getName(), pts);
    }
}
