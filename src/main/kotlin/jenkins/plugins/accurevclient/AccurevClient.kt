package jenkins.plugins.accurevclient


import com.cloudbees.jenkins.plugins.sshcredentials.SSHUserPrivateKey
import com.cloudbees.plugins.credentials.CredentialsMatcher
import com.cloudbees.plugins.credentials.CredentialsMatchers
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials
import jenkins.plugins.accurevclient.commands.*
import jenkins.plugins.accurevclient.model.*

interface AccurevClient {

    var credentials: StandardUsernamePasswordCredentials?

    fun login(): LoginCommand

    fun hist(): HistCommand

    fun update(): UpdateCommand

    fun populate(): PopulateCommand

    fun files(): FilesCommand

    fun keep(): KeepCommand

    fun promote(): PromoteCommand

    fun stream(): StreamCommand

    fun depot(): DepotCommand

    fun workspace(): WorkspaceCommand

    fun add(): AddCommand

    fun changeWS() : ChangeWSCommand

    fun syncTime()

    fun getVersion(): String

    fun getWorkspaces(): AccurevWorkspaces

    fun getReferenceTrees(): AccurevReferenceTrees

    fun getDepots(): AccurevDepots

    fun getStreams(depot: String = ""): AccurevStreams

    fun fetchDepot(depot: String): AccurevDepot?

    fun fetchStream(depot: String, stream: String): AccurevStream?

    fun fetchTransaction(stream: String): AccurevTransaction

    fun fetchTransaction(stream: AccurevStream): AccurevTransaction

    fun getChildStreams(depot: String, stream: String): AccurevStreams

    fun fetchStreamTransactionHistory(stream: String, timeSpec: String, timeSpecUpper: String = "now") : AccurevTransactions

    fun getUpdatesFromAncestors( depot: String, stream : String, timeSpec : Long )  : MutableCollection<AccurevTransaction>

    fun getInfo(): AccurevInfo

    fun getUpdatedElements(
        stream: String,
        latestTransaction: Long,
        previousTransaction: Long,
        referenceTree: Boolean = false
    ): AccurevUpdate

    companion object {
        val verbose = java.lang.Boolean.getBoolean("${AccurevClient::class.java.name}.verbose")
        val CREDENTIALS_MATCHER: CredentialsMatcher = CredentialsMatchers.anyOf(CredentialsMatchers.instanceOf(StandardUsernamePasswordCredentials::class.java), CredentialsMatchers.instanceOf(SSHUserPrivateKey::class.java!!))
    }

    fun getFile(stream: String, path: String, transaction: String = "highest"): String

    fun fileExists(name: String, stream: String): Boolean

    fun getFiles(stream: String): AccurevFiles

    fun fetchDepotTransactionHistory(depot: String, timeSpecLower: String, timeSpecUpper: String, types: Collection<String>) : AccurevTransactions

    fun getNDepthChildStreams(depot: String, stream: String, depth: Long) : Collection<AccurevStream>
}
