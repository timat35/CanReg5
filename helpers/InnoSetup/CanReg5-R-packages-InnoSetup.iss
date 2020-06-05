; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "CanReg5 - R packages"
#define MyParentAppName "CanReg5"
#define MyAppPublisher "IARC"
#define MyAppURL "http://www.iacr.com.fr"
#define MyAppExeName "CanReg.exe"
#define CanReg5Dir "C:\Users\ervikm\Documents\GitHub\CanReg5"
#include "..\..\inno-settings.txt"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{D9F66332-D6B4-4160-BAC8-127AB39FC443}}
SignTool=MsSign $f
AppName={#MyAppName}
; AppVersion={#MyAppVersion}
AppVerName={#MyAppName} 1.0
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyParentAppName}
DefaultGroupName={#MyParentAppName}
OutputDir={#CanReg5Dir}\dist
; InfoBeforeFile={#CanReg5Dir}\changelog.txt
LicenseFile={#CanReg5Dir}\src\canreg\client\gui\resources\gpl-3.0-standalone.txt
AllowNoIcons=yes
OutputBaseFilename=CanReg5-R-Packages-Setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "brazilianportuguese"; MessagesFile: "compiler:Languages\BrazilianPortuguese.isl"
Name: "portuguese"; MessagesFile: "compiler:Languages\Portuguese.isl"
Name: "french"; MessagesFile: "compiler:Languages\French.isl"
Name: "russian"; MessagesFile: "compiler:Languages\Russian.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"
Name: "norwegian"; MessagesFile: "compiler:Languages\Norwegian.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: 

[Files]
; NOTE: Don't use "Flags: ignoreversion" on any shared system files
Source: "{#CanReg5Dir}\conf\tables\r\r-packages\*"; DestDir: "{app}\conf\tables\r\r-packages"; Flags: ignoreversion

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"

[Run]
; Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, "&", "&&")}}"; Flags: nowait postinstall skipifsilent

