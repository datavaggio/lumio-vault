package com.lumiomedical.vault.parser.resolver;

import com.noleme.commons.file.Files;
import com.noleme.commons.file.Resources;
import com.lumiomedical.vault.exception.VaultResolverException;
import com.lumiomedical.vault.parser.resolver.source.Source;

import java.io.IOException;

/**
 * @author Pierre Lecerf (pierre@noleme.com)
 * Created on 21/10/2018
 */
public class FlexibleResolver implements VaultResolver
{
    @Override
    public Source resolve(String origin) throws VaultResolverException
    {
        try {
            if (Files.fileExists(origin))
                return new Source<>(origin, Files.streamFrom(origin));
            return new Source<>(origin, Resources.streamFrom(origin));
        }
        catch (IOException e) {
            throw new VaultResolverException("The configuration file could not be loaded at "+origin+".", e);
        }
    }
}
