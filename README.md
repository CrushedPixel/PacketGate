# PacketGate
A Sponge library allowing plugins to listen to, modify and intercept incoming and outgoing packets.

## Build
To build `PacketGate` on your own machine, clone or download the repository, navigate into the directory and
run `./gradlew setupDecompWorkspace` to set up ForgeGradle and the Access Transformers.  

Run `./gradlew build` to simply build the jar.

Alternatively, run `./gradlew install` to build the jar and deploy it to your local maven repository to use it as a dependency in other projects.  
As the `PacketGate` library is in no public maven repository yet, this is the best approach to use 
`PacketGate` as a dependency as of now.

## Installation
To use `PacketGate` in one of your plugins, you need to use ForgeGradle and add `PacketGate` as a compile-time dependency.
We suggest using [jitpack.io](https://jitpack.io) to depend on `PacketGate` like so:
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.CrushedPixel:PacketGate:0.1.2'
}
```

## Usage
The `PacketGate` library provides a `PacketGate` class that acts as the registry for 
`PacketListener`s and provides `PacketConnection`s for all online players.

You can get an instance of PacketGate using `PacketGateAPI`:
```java
PacketGate packetGate = PacketGateAPI.get();
```
### Writing a PacketListener
The `PacketListener` interface exposes two methods: `onPacketWrite` and `onPacketRead` which 
are called whenever a packet of a type the listener is registered to is received from/sent to a client.

For convenience reasons, the abstract class `PacketListenerAdapter` implements those methods, 
so you only have to override the methods if you actually need them.

```java
/**
 * An example PacketListener filtering certain swear words sent by the client.
 */
public class SwearWordListener extends PacketListenerAdapter {
    
    @Override
    public void onPacketRead(PacketEvent event, PacketConnection connection) {
        if (!(event.packet() instanceof ServerboundChatPacket)) return;
        ServerboundChatPacket packet = (ServerboundChatPacket) event.packet();
        
        if (packet.getMessage().contains("shit") || packet.getMessage().contains("damn")) {
            // cancel the event so the server will act like the client never sent it
            event.setCancelled(true);
            
            // get a Sponge player from the PacketConnection
            Player player = Sponge.getServer().getPlayer(connection.playerUniqueId());
            
            // send the player some warning words
            player.sendMessage(Text.of("Please don't swear!"));
        }
    }
}
```

You can also modify the packet instead of cancelling it, for example:
```java
String censored = packet.getMessage().replaceAll("shit|damn", "****");
event.setPacket(new ServerboundChatPacket(censored));
```

### Registering a PacketListener
When registering a `PacketListener`, you have to provide a `ListenerPriority` and the Packet classes
the listener should listen to:

```java
packetGate.registerListener(
    new ExampleListener(), 
    ListenerPriority.DEFAULT,
    ServerboundChatPacket.class
);
```

A `PacketListener` can be registered globally or for a certain `PacketConnection` only.
To retrieve the `PacketConnection` instance for a certain player, use `PacketGate#connectionByPlayer(Player)`.

```java
public void registerSwearWordListener(Player player) {
    PacketGate packetGate = PacketGateAPI.get();
    PacketConnection connection = packetGate.connectionByPlayer(player).get();
    packetGate.registerListener(
        new SwearWordListener(),
        ListenerPriority.DEFAULT, 
        connection,
        CPacketChatMessage.class);
}
```

To unregister a `PacketListener`, simply call `PacketGate#unregisterListener(PacketListener)` and it won't listen to
any packets sent out to any `PacketConnection` anymore.

Note that a `PacketConnection` can only be retrieved from a `Player` instance after the server has sent out the 
`SPacketLoginSuccess` packet to the client, so any packet sent before the `Play` state may only be handled 
on a global scale!

### Sending packets
You can send any Packet to a client using `PacketConnection#sendPacket(Packet)`.  
This can be used to send packets without the need to modify a `PacketEvent` in a `PacketListener`.

## Examples
You can find a full example plugin using PacketGate at 
[CrushedPixel/PacketGateExamples](https://github.com/CrushedPixel/PacketGateExamples).
