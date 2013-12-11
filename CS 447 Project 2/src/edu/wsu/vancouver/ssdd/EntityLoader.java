package edu.wsu.vancouver.ssdd;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.wsu.vancouver.ssdd.EntityFactory;
import edu.wsu.vancouver.ssdd.EntityFactory.EntityType;


public class EntityLoader {
	private EntityFactory entityFactory;
	
	public EntityLoader(EntityFactory entityFactory) {
		this.entityFactory = entityFactory;
	}
	
	public void loadEntity(String fileRsc) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileRsc));
		String line;
		String[] s;
		while((line = br.readLine()) != null) {
			System.out.println("TEST");
			if (line.equals("")) {
		        break;
		    } else {
		    	s = line.split(" ");
		    }
			
			// EntityType (float)XPosition (float)YPosition (String)Direction
			if (s.length == 4) {
				EntityType entityType = EntityType.fromString(s[0]);
				float cxp = Float.parseFloat(s[1]);
				float cyp = Float.parseFloat(s[2]);
				String direction = new String(s[3]);
				entityFactory.createEntity(entityType, cxp, cyp, direction);
			} // EntityType (float)XPosition (float)YPosition
			else if (s.length == 3) {
				EntityType entityType = EntityType.fromString(s[0]);
				float cxp = Float.parseFloat(s[1]);
				float cyp = Float.parseFloat(s[2]);
				entityFactory.createEntity(entityType, cxp, cyp);
			} // EntityType
			else if (s.length == 1) {
				EntityType entityType = EntityType.fromString(s[0]);
				entityFactory.createEntity(entityType);
			}
		}
		
		br.close();
	}
}
