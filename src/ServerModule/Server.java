package ServerModule;

import ServerModule.util.RequestManager;
import common.util.Request;
import common.util.Response;
import common.util.ResponseCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private int port;
    private RequestManager requestManager;
    private Socket socket;
    private ServerSocket serverSocket;
    private Scanner scanner;


    public Server(int port, RequestManager requestManager) throws IOException {
        this.port = port;
        this.requestManager = requestManager;
    }

    private Response executeRequest(Request request) {
        return requestManager.manage(request);
    }

    public void run() {
        System.out.println("Сервер запущен!");
        boolean processStatus = true;
        scanner = new Scanner(System.in);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    if (!userCommand[0].equals("save")) {
                        System.out.println("Сервер не может сам принимать такую команду!");
                        return;
                    }
                    Response response = executeRequest(new Request(userCommand[0], userCommand[1]));
                    System.out.println(response.getResponseBody());
                }
            } catch (Exception e){}
        };
        Thread thread = new Thread(userInput);
        thread.start();
        while(processStatus) {
            processStatus = processingClientRequest();
        }
    }

    private boolean processingClientRequest(){
        Request request = null;
        Response response = null;
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            scanner = new Scanner(System.in);
            do {
                request = getRequest();
                System.out.println("Получена команда '" + request.getCommandName() + "'");
                response = executeRequest(request);
                System.out.println("Команда '" + request.getCommandName() + "' выполнена");
                sendResponse(response);
            } while (response.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Произошла ошибка при работе с клиентом!");
            System.exit(0);
        }
        return true;
    }

    private Request getRequest() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        InputStream i = socket.getInputStream();
        i.read(getBuffer);
        return deserialize(getBuffer);
    }

    private void sendResponse(Response response) throws IOException {
        byte[] sendBuffer = serialize(response);
        OutputStream o = socket.getOutputStream();
        o.write(sendBuffer);
    }

    private byte[] serialize(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Request deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }
}

