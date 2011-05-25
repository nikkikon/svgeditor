package svgedit.commandManager;

import java.util.ArrayList;

public class CommandStack{
	private final ArrayList<CommandInterface> commands = new ArrayList<CommandInterface>();
	private int currentLocation=-1;
	private int saveLocation = currentLocation;
	
	public int getSize(){
		return commands.size();
	}
	
	public void addCommand(CommandInterface command){
		
		clearInFrontOfCurrent();
	    //command.execute();
		commands.add(command);
		currentLocation++;
		System.out.println("ADD COMMAND STACK");
		System.out.println(command);
	}
	
	private void clearInFrontOfCurrent() {
		while (currentLocation < commands.size() - 1) {
			commands.remove(currentLocation + 1);
		}
	}
	
	public void undo() {
		commands.get(currentLocation).undo();
		currentLocation--;
	}

	public boolean undoEnabled() {
		return currentLocation >= 0;
	}

	public void redo() {
		currentLocation++;
		commands.get(currentLocation).execute();
	}

	public boolean redoEnabled() {
		return currentLocation < commands.size() - 1;
	}

	public boolean dirty() {
		return currentLocation != saveLocation;
	}



}
