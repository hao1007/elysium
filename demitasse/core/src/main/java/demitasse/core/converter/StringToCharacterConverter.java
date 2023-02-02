package demitasse.core.converter;

import static demitasse.core.CoreTools.toCharacter;

/**
 * StringToCharacterConverter
 */
public enum StringToCharacterConverter implements Converter<String, Character> {
    Instance;

    @Override
    public Character convert(String object) {
        return toCharacter(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Character> outputType() {
        return Character.class;
    }

    @Override
    public Converter<Character, String> reverse() {
        return CharacterToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
