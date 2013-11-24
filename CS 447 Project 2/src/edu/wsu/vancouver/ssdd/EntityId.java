package edu.wsu.vancouver.ssdd;

public class EntityId {
	private static int currentID;
	
	public EntityId() {
		currentID = 0;
	}

	public static Integer generateID() {
		Integer entityId = new Integer(currentID);
		if (entityId > Integer.MAX_VALUE) {
			throw new RuntimeException("Overflow occured");
		}
		currentID++;
		return entityId;
	}
}
