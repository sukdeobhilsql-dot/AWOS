# AWOS — Phase 1: MVP Foundation

**Android ko Windows-like Productivity OS banane ka MVP.**
Yeh Phase 1 sirf "Desktop shell" cover karta hai — abhi Linux/Wine runtime nahi hai (woh Phase 2 & 3 me).

## Kya build hua hai

| Feature | Status | File |
|---|---|---|
| Desktop UI (icon grid + wallpaper) | ✅ | `ui/desktop/DesktopScreen.kt` |
| Taskbar (Start button, clock, status icons) | ✅ | `ui/taskbar/Taskbar.kt` |
| Start Menu (all-apps overlay) | ✅ | `ui/startmenu/StartMenu.kt` |
| Settings screen | ✅ | `ui/settings/SettingsScreen.kt` |
| File Explorer (browse storage) | ✅ | `ui/explorer/FileExplorerScreen.kt` + `FileExplorerViewModel.kt` |
| Basic Terminal (simulated shell) | ✅ | `ui/terminal/TerminalScreen.kt` + `TerminalEngine.kt` |
| Download Manager (list Downloads folder) | ✅ | `ui/downloads/DownloadManagerScreen.kt` |
| Splash Screen | ⏳ minimal (boots straight to Desktop) | — |

## Kaise run karein

1. Android Studio (Koala ya newer) me `AWOS-Phase1` folder open karo — "Open an existing project".
2. Gradle sync hone do (internet chahiye pehli baar dependencies download karne ke liye).
3. Ek physical device ya emulator (min SDK 26) connect karo aur Run dabao.
4. Pehli baar app khulte hi storage permission maangega — allow karo taaki File Explorer kaam kare.

## Important notes / limitations (jaanbujh kar Phase 1 ke scope me hai)

- **Terminal abhi real Linux shell nahi hai.** `ls`, `cd`, `pwd`, `mkdir`, `echo`, `whoami`, `date`, `clear` jaise basic commands hi simulate hote hain seedhe Android filesystem par. Real bash + package manager Phase 2 me Termux/proot ke through aayega.
- **File Explorer** Android 13+ (API 33+) par scoped storage ki wajah se sirf kuch folders access kar payega jab tak `MANAGE_EXTERNAL_STORAGE` ya Storage Access Framework add nahi karte — yeh hardening Phase 1 ke baad ka kaam hai.
- **Browser icon** abhi stub hai (Desktop route par point karta hai) — real integration Phase 4 me.

## Next: Phase 2 — Linux Runtime (3–5 weeks)

- [ ] Termux embed / integration
- [ ] Ubuntu ya Debian via proot-distro
- [ ] Real bash terminal (replace `TerminalEngine`)
- [ ] Package manager (apt) support
- [ ] Python, Git, Node.js, Java, PHP runtimes

## Project structure

```
AWOS-Phase1/
 ├── app/
 │   ├── build.gradle.kts
 │   └── src/main/
 │       ├── AndroidManifest.xml
 │       ├── java/com/awos/
 │       │   ├── MainActivity.kt
 │       │   ├── navigation/AwosNavHost.kt
 │       │   └── ui/
 │       │       ├── theme/
 │       │       ├── desktop/
 │       │       ├── taskbar/
 │       │       ├── startmenu/
 │       │       ├── settings/
 │       │       ├── explorer/
 │       │       ├── terminal/
 │       │       └── downloads/
 │       └── res/
 ├── build.gradle.kts
 └── settings.gradle.kts
```
