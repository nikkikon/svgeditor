package svgedit.commandManager;

import java.util.ArrayList;

import svgedit.gui.View;

public class CommandStack{
	private final ArrayList<CommandInterface> commands = new ArrayList<CommandInterface>();
	private int currentLocation=-1;
	private int saveLocation = currentLocation;
	private View view;
	
	public int getSize(){
		return commands.size();
	}
	
	public CommandStack(View view){
		this.view=view;
	}
	
	public void addCommand(CommandInterface command){
		
		clearInFrontOfCurrent();
	    //command.execute();
		commands.add(command);
		currentLocation++;
		
	}
	
	private void clearInFrontOfCurrent() {
		while (currentLocation < commands.size() - 1) {
			commands.remove(currentLocation + 1);
		}
	}
	
	public void undo() {
		commands.get(currentLocation).undo();
		currentLocation--;
		view.getFrame().setUndoEnable();
		
        
	}

	public boolean undoEnabled() {
		return currentLocation >= 1;
	}

	public void redo() {
		view.getFrame().setUndoEnable();
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
