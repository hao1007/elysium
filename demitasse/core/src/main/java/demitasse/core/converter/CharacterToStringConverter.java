package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * CharacterToStringConverter
 */
public enum CharacterToStringConverter implements Converter<Character, String> {
    Instance;

    @Override
    public String convert(Character object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Character> inputType() {
        return Character.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Character> reverse() {
        return StringToCharacterConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
