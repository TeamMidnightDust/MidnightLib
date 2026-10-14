## MidnightLib v1.10.0

- Improved dialogs (with macOS support, finally!)
  - Switch to native operating system file/folder dialogs using SDL (in MC 26.3) or TinyFileDialogs (in older versions)
  - Add built-in color picker in Minecraft style
  - No longer initialize Swing UI (less log spam)
- Add new `MidnightConfig.register(...)` method, replacing `.init(...)` and returning the created config instance
- Fix `ConcurrentModificationException` upon startup in NeoForge environments
- Add Turkish translations (by [@iFair1](https://github.com/iFair1))

### MidnightLib v1.9.3.1

- Fix crash on Neoforge 26.3 due to invalid version range

## MidnightLib v1.9.3

- Dynamic comment formatting (tysm [@Jaffe2718](https://github.com/Jaffe2718))
  - Modders can now provide a list of Strings to format the text with – useful for dynamic stats
- Fix NullPointerException while loading @Condition from the field behind #129 (by [@Jaffe2718](https://github.com/Jaffe2718))
- Updated Tatar translations (by [@Amirhan-Taipovjan-Greatest-I](https://github.com/Amirhan-Taipovjan-Greatest-I))
- Updated Argentine Spanish translations (by [@Texaliuz](https://github.com/Texaliuz))

## MidnightLib v1.9.2

- Add support for using `StringRepresentable` to translate enums

## MidnightLib v1.9.1

- Fix crash when loading existing main config on NeoForge

# MidnightLib v1.9.0

- Setup a **multiversion** build environment 
  - MidnightLib will now always be up-to-date on all relevant versions of Minecraft  
    (Fabric/Forge 1.20.1; Fabric/NeoForge 1.21.1, 1.21.5, 1.21.8, 1.21.10)
  - Measures were taken to ensure this doesn't break mods targeting old MidnightLib versions.  
    In case you still find a broken mod, please [report it](https://github.com/TeamMidnightDust/MidnightLib/issues/new/choose) and tag the issue with `1.9.0`.
- New logo! This offers improved visibility on light themes and a more modern, fresh look. 
- Added JavaDocs to improve the developer experience.
  - To be able to view them, adjust your midnightlib gradle dependency by following the [wiki](https://midnightdust.eu/wiki/midnightlib).
- Reduced jar size – now under 60KB again for Fabric builds :)
- Migrate to Mojang mappings in preparation for upcoming non-obfuscated releases