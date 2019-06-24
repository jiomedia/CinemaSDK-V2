package com.jio.media.library.player;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;

/**
 * A {@link DataSource.Factory} that produces {@link DefaultDataSource} instances that delegate to
 * {@link DefaultHttpDataSource}s for non-file/asset/content URIs.
 */
public final class MediaDefaultDataSourceFactory implements DataSource.Factory
{

    private final Context context;
    private final @Nullable
    TransferListener listener;
    private final DataSource.Factory baseDataSourceFactory;

    /**
     * @param context A context.
     * @param userAgent The User-Agent string that should be used.
     */
    public MediaDefaultDataSourceFactory(Context context, String userAgent) {
        this(context, userAgent, /* listener= */ null);
    }

    /**
     * @param context A context.
     * @param userAgent The User-Agent string that should be used.
     * @param listener An optional listener.
     */
    public MediaDefaultDataSourceFactory(
            Context context, String userAgent, @Nullable TransferListener listener) {
        this(context, listener, new MediaDefaultHttpDataSourceFactory(userAgent, listener));
    }

    /**
     * @param context A context.
     * @param baseDataSourceFactory A {@link DataSource.Factory} to be used to create a base {@link DataSource}
     *     for {@link DefaultDataSource}.
     * @see DefaultDataSource#DefaultDataSource(Context, TransferListener, DataSource)
     */
    public MediaDefaultDataSourceFactory(Context context, DataSource.Factory baseDataSourceFactory) {
        this(context, /* listener= */ null, baseDataSourceFactory);
    }

    /**
     * @param context A context.
     * @param listener An optional listener.
     * @param baseDataSourceFactory A {@link DataSource.Factory} to be used to create a base {@link DataSource}
     *     for {@link DefaultDataSource}.
     * @see DefaultDataSource#DefaultDataSource(Context, TransferListener, DataSource)
     */
    public MediaDefaultDataSourceFactory(
            Context context,
            @Nullable TransferListener listener,
            DataSource.Factory baseDataSourceFactory) {
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.baseDataSourceFactory = baseDataSourceFactory;
    }

    @Override
    public DefaultDataSource createDataSource() {
        DefaultDataSource dataSource =
                new DefaultDataSource(context, baseDataSourceFactory.createDataSource());
        if (listener != null) {
            dataSource.addTransferListener(listener);
        }
        return dataSource;
    }
}
