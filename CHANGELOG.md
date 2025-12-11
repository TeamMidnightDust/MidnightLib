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