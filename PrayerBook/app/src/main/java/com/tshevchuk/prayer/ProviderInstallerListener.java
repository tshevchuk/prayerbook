package com.tshevchuk.prayer;

import android.content.Intent;

import com.google.android.gms.security.ProviderInstaller;

/**
 * android - Javax.net.ssl.SSLHandshakeException: javax.net.ssl.SSLProtocolException:
 * SSL handshake aborted: Failure in SSL library, usually a protocol error - Stack Overflow
 * https://stackoverflow.com/questions/29916962/javax-net-ssl-sslhandshakeexception-javax-net-ssl-sslprotocolexception-ssl-han
 */
public class ProviderInstallerListener implements ProviderInstaller.ProviderInstallListener {
    @Override
    public void onProviderInstalled() {

    }

    @Override
    public void onProviderInstallFailed(int i, Intent intent) {

    }
}
