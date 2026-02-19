Sub ExportAndRunPipeline()
    Dim fileName As String
    Dim i As Integer
    Dim fileNum As Integer
    
    ' Definerer filsti
    fileName = ThisWorkbook.Path & "\legacy_excel\VBA_Export.csv"
    
    ' Eksporterer data til CSV
    fileNum = FreeFile
    Open fileName For Output As #fileNum
    Print #fileNum, "ID;Name;Status;Priority" ' Header
    
    For i = 2 To 10 ' Går gjennom rad 2 til 10
        If Cells(i, 1).Value <> "" Then
            Print #fileNum, Cells(i, 1).Value & ";" & Cells(i, 2).Value & ";" & _
                           Cells(i, 3).Value & ";" & Cells(i, 4).Value
        End If
    Next i
    
    Close #fileNum
    
    MsgBox "Eksport vellykket! Starter BridgeFlow Pipeline...", vbInformation
    
    ' STARTER PIPELINEN AUTOMATISK
    ' Dette kjører .bat-filen direkte fra Excel
    Shell "cmd.exe /c cd /d " & ThisWorkbook.Path & " && run_pipeline.bat", vbNormalFocus
End Sub