' Dette er VBA-kode som ville ligget inne i ingeniørens Excel-ark
Sub ExportRequirementsToBridgeFlow()
    Dim myFile As String
    myFile = ThisWorkbook.Path & "\VBA_Export.csv"
    
    Open myFile For Output As #1
    Print #1, "ID;Requirement_Name;Description;Priority"
    
    ' Simulerer gjennomgang av rader i Excel
    Print #1, "VBA-101;Fuel_System;Must handle jet fuel A1;High"
    Print #1, "VBA-102;Wing_Span;Maximum 12 meters;Medium"
    
    Close #1
    MsgBox "Data eksportert til BridgeFlow Java-plugin!"
End Sub