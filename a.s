.data
.text
.align 2
.globl main
main:
	addi $sp, $sp, -4 # move up a cell
	move $fp, $sp     # start using memory here
	move $gp, $sp     # set global pointer (unused)
	jal Label_main
end_main:
	li $v0, 10        # exit
	syscall
Label_func:
	sw $31, 0($sp)
	lw $t1, 0($fp)
	lw $t2, -4($fp)
	add $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $v0, -8($fp)
	addi $sp, $fp, 0
	lw $t0, 0($sp)
	add $fp, $fp, $t0
	lw $31, 0($fp)
	jr $31
	addi $sp, $fp, 0
	lw $t0, 0($sp)
	add $fp, $fp, $t0
	lw $31, 0($fp)
	jr $31
Label_main:
	sw $31, 0($sp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 4($sp)
	add $fp, $0, $sp
	li $a0, 2
	addi $sp, $sp, -4
	sw $a0, 0($sp)
	li $t0, 2
	addi $sp, $sp, -4
	sw $t0, 0($sp)
	li $a1, 2
	addi $sp, $sp, -4
	sw $a1, 0($sp)
	li $t0, 2
	addi $sp, $sp, -4
	sw $t0, 0($sp)
	j Label_func
Label_1:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 4($sp)
	add $fp, $0, $sp
	li $a0, 4
	addi $sp, $sp, -4
	sw $a0, 0($sp)
	li $t0, 4
	addi $sp, $sp, -4
	sw $t0, 0($sp)
	j Label_printf
Label_2:
	li $v0, 0
	addi $sp, $fp, 0
	lw $t0, 0($sp)
	add $fp, $fp, $t0
	lw $31, 0($fp)
	jr $31
	addi $sp, $fp, 0
	lw $t0, 0($sp)
	add $fp, $fp, $t0
	lw $31, 0($fp)
	jr $31
Label_printf:
	lw $a0, 0($sp)
	move $a0, $v0
	li $v0, 1         # print_int
	syscall
	jr $31

