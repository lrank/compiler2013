.data
.text
.align 2
.globl main
main:
	move $gp, $sp     # set global pointer (unused)
	sw $0, 0($sp)
	sw $0, -4($sp)
	addi $sp, $sp, -8 # move up a cell
	move $fp, $sp     # start using memory here
	jal Label_main
end_main:
	li $v0, 10        # exit
	syscall
Label_nfactor:
	sw $31, 4($fp)
	addi $sp, $sp, -16
	lw $t1, 0($fp)
	li $t2, 0
	sub $t0, $t1, $t2
	sw $t0, -4($fp)
	lw $t0, -4($fp)
	bnez $t0, Label_1
	li $v0, 1
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_1:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	add $v1, $0, $sp
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $a0, -8($fp)
	sw $a0, -0($sp)
	add $fp, $v1, $0
	jal Label_nfactor
	sw $v0, -12($fp)
Label_2:
	lw $t1, 0($fp)
	lw $t2, -12($fp)
	mul $t0, $t1, $t2
	sw $t0, -12($fp)
	lw $v0, -12($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_main:
	sw $31, 4($fp)
	addi $sp, $sp, -4
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	add $v1, $0, $sp
	li $a0, 6
	sw $a0, -0($sp)
	add $fp, $v1, $0
	jal Label_nfactor
	sw $v0, -4($fp)
Label_3:
	lw $t1, -4($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	add $v1, $0, $sp
	lw $a0, 0($fp)
	sw $a0, -0($sp)
	add $fp, $v1, $0
	jal Label_printf
	sw $v0, -4($fp)
Label_4:
	lw $v0, 0($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_printf:
	sw $31, 4($fp)
	addi $sp, $sp, -4
	lw $a0, 0($fp)
	li $v0, 1         # print_int
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	syscall
	jr $31

