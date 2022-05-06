package ServerModule;

import ServerModule.commands.*;
import ServerModule.util.CollectionManager;
import ServerModule.util.CommandManager;
import ServerModule.util.FileManager;
import ServerModule.util.RequestManager;

import java.io.IOException;

public class MainServer {
    public static final int PORT = 8778;

    public static void main(String[] args) throws IOException {
        String path = null;
        try {
            path = args[0];
            FileManager fileManager = new FileManager(path);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager),
                    new UpdateCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new AddIfMinCommand(collectionManager),
                    new RemoveGreaterCommand(collectionManager),
                    new HistoryCommand(),
                    new PrintAscendingCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new PrintFiendDescendingMpaaRatingCommand(collectionManager),
                    new LoadCollectionCommand(collectionManager));
            RequestManager requestManager = new RequestManager(commandManager);
            Server server = new Server(PORT, requestManager);
            server.run();
            collectionManager.saveCollection();
        } catch (IndexOutOfBoundsException f){
            System.out.println("Файл не передан программе");
        }
        catch (NullPointerException e){
            System.out.println("Указанного файла не существует");
        }
    }
}
