# Mini-Project
- ค่า Resistance จะถูกเก็บไว้ในตัวแปร private double resistance; ภายใน Edge
- มีฟังก์ชั่น addVertex
- มีฟังก์ชั่น findVertex
- มีฟังก์ชั่น addEdge (เป็นการ add แบบ Undirected Edge ไปและกลับได้ เอาไว้เช็คว่า 2 Node เชื่อมกันแค่ตัวมันเองจริงๆ)
- ฟังก์ชั่น circuitReductionParallel ทำงานโดยการวนทุก Vertex แล้วหาว่าตัว Edge ไหนที่มี destination เหมือนกัน แล้วก็ยุบวงจรแบบขนานลง