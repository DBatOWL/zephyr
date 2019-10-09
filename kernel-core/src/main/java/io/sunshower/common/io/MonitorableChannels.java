package io.sunshower.common.io;

import io.sunshower.kernel.PluginRegistration;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.CompletableFuture;

public class MonitorableChannels {

    public static ReadableByteChannel from(URL url, MonitorableByteChannel.Listener listener) throws IOException {
        val connection = url.openConnection();
        connection.connect();
        val stream = connection.getInputStream();
        val len    = connection.getContentLengthLong();
        return new MonitorableByteChannel(Channels.newChannel(stream), listener, len);
    }

    public static MonitorableFileTransfer transfer(URL url, File destination) throws IOException {
        val connection = url.openConnection();
        connection.connect();
        val stream = connection.getInputStream();
        val len    = connection.getContentLengthLong();
        return new MonitorableFileTransfer(destination, len, Channels.newChannel(stream));

    }


}