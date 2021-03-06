package com.lumiomedical.vault.parser;

import com.lumiomedical.vault.container.definition.Definitions;
import com.lumiomedical.vault.container.definition.ServiceProvider;
import com.lumiomedical.vault.exception.VaultParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Pierre Lecerf (plecerf@lumiomedical.com)
 * Created on 2020/11/28
 */
public class AdjusterTest
{
    @Test
    void testVariable() throws VaultParserException
    {
        var parser = new VaultCompositeParser();
        int value = 1234;

        Definitions def = parser.extract("com/lumiomedical/vault/parser/simple.json", new Definitions(), defs -> {
            defs.setVariable("my_variable", value);
        });

        Assertions.assertEquals(value, def.getVariable("my_variable"));
    }

    @Test
    void testResolving() throws VaultParserException
    {
        var parser = new VaultCompositeParser();
        var string = "this is an entirely new string";

        Definitions defA = parser.extract("com/lumiomedical/vault/parser/simple.json");
        Assertions.assertEquals("SomeString", ((ServiceProvider)defA.getDefinitions().get("provider.string")).getMethodArgs()[0]);

        Definitions defB = parser.extract("com/lumiomedical/vault/parser/simple.json", new Definitions(), defs -> {
            defs.setVariable("provider.string.base_value", string);
        });
        Assertions.assertEquals(string, ((ServiceProvider)defB.getDefinitions().get("provider.string")).getMethodArgs()[0]);
    }

    @Test
    void testReplacement() throws VaultParserException
    {
        var parser = new VaultCompositeParser();
        var string = "this is an entirely new string";

        Definitions defA = parser.extract("com/lumiomedical/vault/parser/simple.json");
        Assertions.assertEquals("SomeString", ((ServiceProvider)defA.getDefinitions().get("provider.string")).getMethodArgs()[0]);

        Definitions defB = parser.extract("com/lumiomedical/vault/parser/simple.json", new Definitions(), defs -> {
            defs.setVariable("provider.string.value", string);
        });
        Assertions.assertEquals(string, ((ServiceProvider)defB.getDefinitions().get("provider.string")).getMethodArgs()[0]);
    }
}
