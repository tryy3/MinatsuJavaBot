var net = require("net");
var Skyweb = require("skyweb");

var skyweb = new Skyweb();

var username = process.argv[2];
var password = process.argv[3];
if (!username || !password) {
    throw new Error('Username and password should be provided as commandline arguments!');
}

skyweb.login(username, password).then(function(acc)
{
    console.log("Skype is now logged in.");
})

var HOST = "127.0.0.1";
var PORT = "1337";

net.createServer(function(sock)
{

    // We have a connection - a socket object is assigned to the connection automatically
    console.log('CONNECTED: ' + sock.remoteAddress + ':' + sock.remotePort);

    // Add a 'data' event handler to this instance of socket
    sock.on('data', function(data)
    {
        console.log(data.toString().substring(2));
        var dataJson = JSON.parse(data.toString().substring(2));
        console.log(dataJson);
        if (dataJson.length < 2) {
            console.log("The data does not have enough arguments.")
            console.log('DATA ' + sock.remoteAddress + ': ' + data);
            return;
        }

        if (dataJson[0] == "sendMessage") {
            if(dataJson[1].length < 2) {
                console.log("The arguments is invalid.")
                console.log('DATA ' + sock.remoteAddress + ': ' + data);
                return;
            }

            skyweb.sendMessage(dataJson[1][0], dataJson[1][1]);
        }
        // Write the data back to the socket, the client will receive it as data from the server
        //sock.write('You said "' + data + '"\n');

    });
    skyweb.messagesCallback = function(messages)
    {
        messages.forEach(function(message)
        {
            if (message.resource.from.indexOf("tryy3.bot") === -1 && message.resource.messagetype !== 'Control/Typing' && message.resource.messagetype !== 'Control/ClearTyping')
            {
                var link = message.resource.conversationLink;
                var id = link.substring(link.lastIndexOf("/") + 1);
                var from = message.resource.from.substring(message.resource.from.lastIndexOf('/') + 1);
                sock.write(JSON.stringify(["onMessageEvent", [id, from, message.resource.content]]) + "\n");
            }
        });
    }
    // Add a 'close' event handler to this instance of socket
    sock.on('close', function(data)
    {
        console.log('CLOSED: ' + sock.remoteAddress + ' ' + sock.remotePort);
    });

}).listen(PORT, HOST);

console.log('Server listening on ' + HOST + ':' + PORT);