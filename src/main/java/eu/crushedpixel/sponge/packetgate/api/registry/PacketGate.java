package eu.crushedpixel.sponge.packetgate.api.registry;

import com.google.common.base.Preconditions;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener.ListenerPriority;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener.PacketListenerData;
import net.minecraft.network.Packet;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PacketGate extends ListenerOwner {

    private Set<PacketConnection> connections = new HashSet<>();

    public void registerConnection(PacketConnection connection) {
        // register all global packet listeners to the connection
        this.packetListeners.values().forEach(list -> list.forEach(connection::register));

        connections.add(connection);
    }

    public void unregisterConnection(PacketConnection connection) {
        connections.remove(connection);
    }

    public Optional<PacketConnection> connectionByPlayer(Player player) {
        return connections
                .stream()
                .filter(connection -> player.getUniqueId().equals(connection.getPlayerUUID()))
                .findFirst();
    }

    public void registerListener(PacketListener packetListener, ListenerPriority priority,
                                 Class... packetClasses) {
        registerListener(packetListener, priority, null, packetClasses);
    }

    public void registerListener(PacketListener packetListener, ListenerPriority priority,
                                 PacketConnection connection,
                                 Class... packetClasses) {
        Preconditions.checkArgument(packetClasses.length > 0,
                "Listener has to be registered for at least one Packet class");

        List<Class<? extends Packet>> classes = new ArrayList<>();

        // check if packet classes are valid
        for (Class clazz : packetClasses) {
            Preconditions.checkArgument(Packet.class.isAssignableFrom(clazz),
                    "Packet classes have to be subclasses of net.minecraft.network.Packet");

            classes.add(clazz);
        }

        PacketListenerData packetListenerData = new PacketListenerData(packetListener, priority, classes);

        if (connection != null) {
            connection.register(packetListenerData);
        } else {
            this.register(packetListenerData);
        }
    }

    public void unregisterListener(PacketListener packetListener) {
        unregister(packetListener);

        for (PacketConnection connection : connections) {
            connection.unregister(packetListener);
        }
    }

}
