package _learn.jars._reactor.example;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.Environment;
import reactor.rx.Streams;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by root on 8/18/17.
 */
public class FileStream
{
    public static void main(String args[])
    {
        Publisher<String> fileStream = new Publisher<String>()
        {
            @Override
            public void subscribe(final Subscriber<? super String> subscriber)
            {
                final File file = new File("settings.gradle");
                try
                {
                    final BufferedReader is = new BufferedReader(new FileReader(file));

                    subscriber.onSubscribe(new Subscription()
                    {
                        final AtomicBoolean terminated = new AtomicBoolean(false);

                        @Override
                        public void request(long n)
                        {
                            long requestCursor = 0l;
                            try
                            {
                                String line;
                                while ((requestCursor++ < n || n == Long.MAX_VALUE)
                                        && !terminated.get())
                                {

                                    line = is.readLine();
                                    if (line != null)
                                    {
                                        subscriber.onNext(line);
                                    } else
                                    {
                                        if (terminate())
                                        {
                                            subscriber.onComplete();
                                        }
                                        return;
                                    }
                                }
                            } catch (IOException e)
                            {
                                if (terminate())
                                {
                                    subscriber.onError(e);
                                }
                            }
                        }

                        @Override
                        public void cancel()
                        {
                            terminate();
                        }

                        private boolean terminate()
                        {
                            if (terminated.compareAndSet(false, true))
                            {
                                try
                                {
                                    is.close();
                                } catch (Exception t)
                                {
                                    subscriber.onError(t);
                                }
                                return true;
                            }
                            return false;
                        }
                    });

                } catch (FileNotFoundException e)
                {
                    Streams.<String, FileNotFoundException>fail(e)
                            .subscribe(subscriber);
                }
            }
        };

        Streams.wrap(fileStream)
                .capacity(4L)
                .consumeOn(
                        Environment.sharedDispatcher(),
                        System.out::println,
                        Throwable::printStackTrace,
                        nothing -> System.out.println("## EOF ##"));
    }

}
