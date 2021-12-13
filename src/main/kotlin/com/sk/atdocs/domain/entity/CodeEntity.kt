package com.sk.atdocs.domain.entity
import com.sk.atdocs.app.enum.CodeGroup
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "TB_CODE")
class CodeEntity (
        codeGroup: CodeGroup,
        codeKey: String,
        codeName : String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    @NotNull
    @Column(name = "cd_gp")
    val codeGroup: String? = codeGroup.codeGroup

    @NotNull
    @Column(name = "cd_key")
    val codeKey: String? = codeKey

    @Column(name = "cd_nm")
    val codeName: String? = codeName

}