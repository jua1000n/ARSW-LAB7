package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.FiltrPersistence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Redundancies implements FiltrPersistence {

    @Override
    public Blueprint Filtr(Blueprint bp) {
        List<Point> pts = new ArrayList<Point>();
        for(int i = 0; i< bp.getPoints().size(); i++) {
            if (i != (bp.getPoints().size()-1)) {
                if (!(bp.getPoints().get(i).getX() == (bp.getPoints().get(i + 1).getX()))) {
                    if (!(bp.getPoints().get(i).getY() == (bp.getPoints().get(i + 1).getY()))) {
                        pts.add(bp.getPoints().get(i));
                    }
                }
            } else {
                pts.add(bp.getPoints().get(i));
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), pts);
    }
}
